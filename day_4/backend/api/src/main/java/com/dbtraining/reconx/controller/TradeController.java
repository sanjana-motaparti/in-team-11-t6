package com.dbtraining.reconx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/trades")
public class TradeController {

  private static final Logger logger = LoggerFactory.getLogger(TradeController.class);

  @GetMapping
  public String getTrades() {
    logger.info("Processing trade request");
    return "Trade list response";
  }
}
