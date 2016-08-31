package edu.deanza.calendar.dal;

import java.util.ArrayList;
import java.util.List;

import edu.deanza.calendar.domain.EventRepository;
import edu.deanza.calendar.domain.OrganizationRepository;
import edu.deanza.calendar.domain.models.Event;
import edu.deanza.calendar.domain.models.Organization;
import edu.deanza.calendar.domain.models.OrganizationEvent;

/**
 * Created by karinaantonio on 8/18/16.
 */

public class FirebaseOrganizationEvent extends OrganizationEvent {

    public FirebaseOrganizationEvent(Organization organization) {
        super(organization);
    }

    public FirebaseOrganizationEvent(Event event) {
        super(event);
    }

    @Override
    public Organization getOrganization() {
        if (organization == null) {
            assert event != null;
            OrganizationRepository repository = new FirebaseOrganizationRepository();
            organization = repository.findByName(event.getOrganizationName()).getOrganization();
        }
        return organization;
    }

    @Override
    public List<Event> allEvents() {
        if (organization == null) {
            getOrganization();
        }

        EventRepository repository = new FirebaseEventRepository();
        List<OrganizationEvent> organizationEvents = repository.findByOrganization(organization.getName());
        List<Event> events = new ArrayList<>();
        for (OrganizationEvent e : organizationEvents) {
            events.add(e.getEvent());
        }
        return events;
    }

}