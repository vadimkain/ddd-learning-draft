package com.example.domain_events;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

interface IContainer {
    <T> List<T> resolveAll(Class<Handles> handlesClass, Class<? extends IDomainEvent> aClass);
}

public class DomainEvents {
    //    на каждый поток свой собственный коллбэк
    private static final ThreadLocal<List<Consumer<? extends IDomainEvent>>> actions = ThreadLocal.withInitial(CopyOnWriteArrayList::new);
    private static IContainer container;

    //    что-то типа ioc
    public static void setContainer(IContainer container) {
        DomainEvents.container = container;
    }

    //    Регистрируем коллбэки для указанного доменного события
    public static <T extends IDomainEvent> void register(Class<T> eventType, Consumer<T> callback) {
        List<Consumer<? extends IDomainEvent>> actions = DomainEvents.actions.get();
        actions.add(callback);
    }

    //    Очищаем коллбэки, переданные в register в текущем потоке
    public static void clearCallbacks() {
        actions.remove();
    }

    public static <T extends IDomainEvent> void raise(T event) {
        if (container != null) {
            for (Object handler : container.resolveAll(Handles.class, event.getClass())) {
                ((Handles<T>) handler).handle(event);
            }
        }

        List<Consumer<? extends IDomainEvent>> actions = DomainEvents.actions.get();
        for (Consumer<? extends IDomainEvent> handler : actions) {
            if (handler != null) {
                ((Consumer<T>) handler).accept(event);
            }
        }
    }
}
