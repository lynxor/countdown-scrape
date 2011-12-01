package za.co.whenis.agent

import dispatch._
import za.co.whenis.ParsedEvent

/**
 * User: dawidmalan
 * Date: 2011/12/01
 * Time: 3:25 PM
 */

object PushAgent {
  val pushUrl = "http://localhost:55555/countdown/new"


  def pushEvents(event: ParsedEvent) = {
    val http = new Http
    val req = url(pushUrl) <<? (event.toParams)

    http(req >- {
      (s: String) => println(s) //do something with results?
    })
  }
}