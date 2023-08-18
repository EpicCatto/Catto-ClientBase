package epiccatto.catto.event.impl;

import epiccatto.catto.event.Event;

public class EventSafeWalk extends Event
{
    public boolean safe;
    
    public EventSafeWalk(final boolean safe) {
        this.safe = safe;
    }
    
    public void setSafe(final boolean safe) {
        this.safe = safe;
    }
}
