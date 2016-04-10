package com.apm.repos.audit;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.apm.repos.RecordAuditRepository;
import com.apm.utils.AutowireHelper;

@Configurable(autowire = Autowire.BY_TYPE, dependencyCheck = true)
public class AuditEntityListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuditEntityListener.class);

	@Autowired
	private RecordAuditRepository recordAuditRepo;
	
	@PrePersist
	public void prePersist(AuditEntity e) {

		AutowireHelper.autowire(this, this.recordAuditRepo);

		String currentAuditor = getCurrentAuditor();
		Date currentDate = new Date();

		AuditRecord auditRecord = new AuditRecord();

		auditRecord.setCreatedBy(currentAuditor);
		auditRecord.setCreationTime(currentDate);
		auditRecord.setLastModifiedBy(currentAuditor);
		auditRecord.setModificationTime(currentDate);
		auditRecord.setOperation("CREATE");

		AuditRecord auditRecordSaved = recordAuditRepo.save(auditRecord);
		e.setAuditRecord(auditRecordSaved);

	}

	@PreUpdate
	public void preUpdate(AuditEntity e) {

		AutowireHelper.autowire(this, this.recordAuditRepo);

		String currentAuditor = getCurrentAuditor();
		Date currentDate = new Date();

		// now update the record
		AuditRecord auditRecord = new AuditRecord();
		auditRecord.setLastModifiedBy(currentAuditor);
		auditRecord.setModificationTime(currentDate);
		auditRecord.setOperation("UPDATE");

		AuditRecord auditRecordSaved = recordAuditRepo.save(auditRecord);
		e.setAuditRecord(auditRecordSaved);

	}

	public String getCurrentAuditor() {
		LOGGER.debug("Getting the username of authenticated user.");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			LOGGER.debug("Current user is anonymous. Returning null.");
			return null;
		}

		String username = authentication.getName();
		LOGGER.debug("Returning username: {}", username);

		return username;
	}
}
