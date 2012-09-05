package com.typeahead.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.annconia.api.repository.AfterSaveEvent;
import com.annconia.api.repository.RepositoryEvent;
import com.typeahead.entity.Account;
import com.typeahead.repository.AccountRepository;

@Component
public class AccountListener implements ApplicationListener<RepositoryEvent> {

	@Autowired(required = false)
	private AccountRepository repository;

	public final void onApplicationEvent(RepositoryEvent event) {
		if ((event.getSource() instanceof Account)) {
			Account entity = (Account) event.getSource();
			if (event instanceof AfterSaveEvent) {
				onAfterSave(entity);
			}

			return;
		}
	}

	/**
	 * Override this method if you are interested in {@literal afterSave} events.
	 *
	 * @param entity
	 */
	protected void onAfterSave(Account entity) {

	}

}
