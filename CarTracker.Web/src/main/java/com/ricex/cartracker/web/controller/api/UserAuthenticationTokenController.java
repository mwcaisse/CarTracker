package com.ricex.cartracker.web.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ricex.cartracker.common.entity.auth.UserAuthenticationToken;
import com.ricex.cartracker.common.viewmodel.EntityResponse;
import com.ricex.cartracker.common.viewmodel.PagedEntity;
import com.ricex.cartracker.common.viewmodel.SortParam;
import com.ricex.cartracker.data.manager.auth.UserAuthenticationTokenManager;
import com.ricex.cartracker.data.query.properties.EntityType;

@Controller
@RequestMapping("api/user/tokens")
public class UserAuthenticationTokenController extends ApiController<UserAuthenticationToken> {

	private final UserAuthenticationTokenManager manager;
	
	public UserAuthenticationTokenController(UserAuthenticationTokenManager manager) {
		super(EntityType.USER_AUTHENTICATION_TOKEN, manager);

		this.manager = manager;
	}
	
	@RequestMapping(value="/active", method=RequestMethod.GET, produces = {JSON})
	public @ResponseBody EntityResponse<PagedEntity<UserAuthenticationToken>> getActiveForUser(
			@RequestParam(name = "startAt", required = false, defaultValue = DEFAULT_START_AT) int startAt,
			@RequestParam(name = "maxResults", required = false, defaultValue = DEFAULT_MAX_RESULTS) int maxResults,
			SortParam sort) {		
		return createEntityResponse(manager.getActiveForUser(getCurrentUser().getId(), startAt, maxResults, sort));
	}

}
