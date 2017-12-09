package org.fsoft.tms.event;

import org.fsoft.tms.entity.Topic;
import org.fsoft.tms.entity.User;
import org.springframework.context.ApplicationEvent;

/**
 * Created by Isabella on 8-Jun-2017.
 */
public class OnAssignTopicCompleteEvent extends ApplicationEvent{

    private Integer trainerId;

    private Integer topicId;

    public OnAssignTopicCompleteEvent(Integer trainerId, Integer topicId) {
        super(trainerId);
        this.trainerId = trainerId;
        this.topicId = topicId;
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }
}
