package com.github.ratel.controllers.impl;

import com.github.ratel.controllers.ApiSecurityHeader;
import com.github.ratel.controllers.interfaces.ManagerController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.github.ratel.utils.ApiPathConstants.API_PREFIX;
import static com.github.ratel.utils.ApiPathConstants.MANAGER;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(API_PREFIX + MANAGER)
public class ManagerControllerImpl implements ApiSecurityHeader, ManagerController {

}
