package soul.dev.productservice.command.controllers;

import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/admin/management")
public class EventReplayRestController {

    @Autowired
    private EventProcessingConfiguration eventProcessingConfiguration ;

    @PostMapping("/replayEvent/{processName}")
    public Boolean replayEvents(@PathVariable String processName) {
        Optional<TrackingEventProcessor> trackingEventProcessor = eventProcessingConfiguration.eventProcessor(processName, TrackingEventProcessor.class);
        if (trackingEventProcessor.isPresent()) {
            TrackingEventProcessor eventProcessor = trackingEventProcessor.get();
            eventProcessor.shutDown();
            eventProcessor.resetTokens();
            eventProcessor.start();
            return true;
        }
        return false;
    }
}
