package za.co.whenis.main

import za.co.whenis.agent.PushAgent
import za.co.whenis.ParsedEvent
import org.joda.time.DateTime

/**
 * User: dawidmalan
 * Date: 2011/12/01
 * Time: 3:39 PM
 */

object ScrapeAndPush extends App{

  PushAgent.pushEvents(ParsedEvent("testpush", new DateTime, List("test, whatever, stuff")))

}