package za.co.whenis.scrape

import org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
import xml.parsing.NoBindingFactoryAdapter
import java.io.InputStream

/**
 * User: dawidmalan
 * Date: 2011/12/05
 * Time: 10:47 AM
 */

trait Scraper {
  val parserFactory = new SAXFactoryImpl
  val parser = parserFactory.newSAXParser
  val adapter = new NoBindingFactoryAdapter

  def getOnline : Seq[ParsedEvent]
  def getFrom(is: InputStream) : Seq[ParsedEvent]
}