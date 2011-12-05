package za.co.whenis.agent

import dispatch._
import za.co.whenis.scrape.ParsedEvent

/**
 * User: dawidmalan
 * Date: 2011/12/01
 * Time: 3:25 PM
 */

class PushAgent(host: String, port: Int) {
  val pushUrl = :/(host, port) / "countdown" / "upsert"


  def pushEvents(event: ParsedEvent) = {
    val http = new Http
    val req = pushUrl <<? (event.toParams)

    http(req >- {
      (s: String) => println(s) //do something with results?
    })
  }
}