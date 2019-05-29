package gr.xe.java.codechallenge.statistics.ads.infrastructure;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Random;

/**
 * Model representing the ad entity
 */
@Entity
@Table(name = "ads")
@EntityListeners(AuditingEntityListener.class)
public class Ad {

    /**
     * Default constructor
     */
    public Ad() {
    }

    public Ad(String id) {
        this.id = id;
    }

    @Id
    @NotNull
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    protected String id;

    @Column(name = "text")
    protected String text = "This is the ad text";

    @Column(name = "customerId", nullable = false)
    protected int customerId = Math.abs(new Random().nextInt(500));

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt", nullable = false)
    protected Date createdAt;


    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets text.
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets customer id.
     *
     * @return the customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets customer id.
     *
     * @param customerId the customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the ad created date.
     *
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets ad created date.
     *
     * @param createdAt the createdAt date
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Ad{" +
            "id=" + id +
            ", text='" + text + '\'' +
            ", customerId='" + customerId + '\'' +
            ", createdAt=" + createdAt +
            '}';
    }
}