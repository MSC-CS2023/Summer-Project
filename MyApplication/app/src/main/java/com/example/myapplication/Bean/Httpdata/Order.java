package com.example.myapplication.Bean.Httpdata;

import org.jetbrains.annotations.NotNull;

public class Order {
    /**
     * The time when the order is canceled.
     */
    private Long cancelTimestamp;
    /**
     * The time when the order is confirmed.
     */
    private Long confirmationTimestamp;
    /**
     * The time when the order is confirmed.
     */
    private Long creationTimestamp;
    /**
     * The id of the order creator.
     */
    private Long customerId;
    /**
     * The time when the service end.
     */
    private Long endTimestamp;
    /**
     * The time when the service finished.
     */
    private Long finishTimestamp;
    /**
     * Order id.
     */
    private Long id;
    /**
     * Is the order canceled by the creater.
     */
    private Boolean isCanceled;
    /**
     * Is the order confirmed by service provider.
     */
    private Boolean isConfirmed;
    /**
     * Is the service finished.
     */
    private Boolean isFinished;
    /**
     * Is the order rejected by service provider.
     */
    private Boolean isRejected;
    /**
     * The mark given by the customer for the order.
     */
    private Long mark;
    /**
     * The time when the order is rejected.
     */
    private Long rejectionTimestamp;
    /**
     * The id of the service related to the order.
     */
    private Long serviceId;
    private ServiceShort serviceShort;
    /**
     * The time when the service start.
     */
    private Long startTimestamp;

    public Long getCancelTimestamp() { return cancelTimestamp; }
    public void setCancelTimestamp(Long value) { this.cancelTimestamp = value; }

    public Long getConfirmationTimestamp() { return confirmationTimestamp; }
    public void setConfirmationTimestamp(Long value) { this.confirmationTimestamp = value; }

    public Long getCreationTimestamp() { return creationTimestamp; }
    public void setCreationTimestamp(Long value) { this.creationTimestamp = value; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long value) { this.customerId = value; }

    public Long getEndTimestamp() { return endTimestamp; }
    public void setEndTimestamp(Long value) { this.endTimestamp = value; }

    public Long getFinishTimestamp() { return finishTimestamp; }
    public void setFinishTimestamp(Long value) { this.finishTimestamp = value; }

    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }

    public Boolean getIsCanceled() { return isCanceled; }
    public void setIsCanceled(Boolean value) { this.isCanceled = value; }

    public Boolean getIsConfirmed() { return isConfirmed; }
    public void setIsConfirmed(Boolean value) { this.isConfirmed = value; }

    public Boolean getIsFinished() { return isFinished; }
    public void setIsFinished(Boolean value) { this.isFinished = value; }

    public Boolean getIsRejected() { return isRejected; }
    public void setIsRejected(Boolean value) { this.isRejected = value; }

    public Long getMark() { return mark; }
    public void setMark(Long value) { this.mark = value; }

    public Long getRejectionTimestamp() { return rejectionTimestamp; }
    public void setRejectionTimestamp(Long value) { this.rejectionTimestamp = value; }

    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long value) { this.serviceId = value; }

    public ServiceShort getServiceShort() { return serviceShort; }
    public void setServiceShort(ServiceShort value) { this.serviceShort = value; }

    public Long getStartTimestamp() { return startTimestamp; }
    public void setStartTimestamp(Long value) { this.startTimestamp = value; }

}
