package com.firegame.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.firegame.service.GameStateService;
import com.firegame.service.MessageService;
import com.firegame.service.RelationshipService;
import com.firegame.service.UserService;

public class BaseController {
	@Autowired
	GameStateService gameStateService;
	@Autowired
	RelationshipService relationshipService;
	@Autowired
	MessageService messageService;
	@Autowired
	UserService userService;
}
