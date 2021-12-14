package helloeventsourcing.events;

public interface SomeEntityEventHandler {
    void apply(ContactDataChanged event);
    void apply(NameTypoFixed event);
    //De created Event is 'anders', want dan is er nog geen object om 'm te accepteren
}
