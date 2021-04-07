package com.games.online.shop.domain;

import com.sun.istack.NotNull;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

//TODO: Tentative entity; might delete pending sessions investigation/implementation

@Entity
@Table(name = "spring_session")
public class SpringSession implements Serializable {
    @Id
    @Size(max = 36)
    @Column(name = "primary_id", length = 36, columnDefinition = "char")
    private String primaryId;

    @NotNull
    @Size(max = 36)
    @Column(name = "session_id", length = 36, nullable = false, columnDefinition = "char")
    private String sessionId;

    @NotNull
    @Column(name = "creation_time", nullable = false)
    private Long creationTime;

    @NotNull
    @Column(name = "last_access_time", nullable = false)
    private Long lastAccessTime;

    @NotNull
    @Column(name = "max_inactive_interval", nullable = false)
    private Integer maxInactiveInterval;

    @NotNull
    @Column(name = "expiry_time", nullable = false)
    private Long expiryTime;

    @NotNull
    @Size(max = 100)
    @Column(name = "principal_name", length = 100, nullable = false)
    private String principalName;

    public String getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    public Long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Integer getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    public void setMaxInactiveInterval(Integer maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }

    public Long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpringSession)) return false;
        SpringSession that = (SpringSession) o;
        return primaryId.equals(that.primaryId) && Objects.equals(sessionId, that.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primaryId, sessionId);
    }

    @Override
    public String toString() {
        return (
            "SpringSession{" +
            "primaryId='" +
            primaryId +
            '\'' +
            ", sessionId='" +
            sessionId +
            '\'' +
            ", creationTime=" +
            creationTime +
            ", lastAccessTime=" +
            lastAccessTime +
            ", maxInactiveInterval=" +
            maxInactiveInterval +
            ", expiryTime=" +
            expiryTime +
            ", principalName='" +
            principalName +
            '\'' +
            '}'
        );
    }
}
