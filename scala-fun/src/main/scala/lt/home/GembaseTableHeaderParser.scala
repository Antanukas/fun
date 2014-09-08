package lt.home

import scala.collection.JavaConversions
import scala.util.parsing.combinator.JavaTokenParsers

/**
 *
 * @author Antanas Bastys
 */
class GembaseTableHeaderParser extends JavaTokenParsers {
  val word = "\\w+".r
  val propertySeparator = "&"
  val wordsBetweenQuetes = "\"" ~> "\\w+".r <~ "\""

  val defFieldDirective = "DEFINE FIELD"
  val columnName = defFieldDirective ~> word <~ propertySeparator

  val propertyName = "/" ~> word
  val propertyValue = (wordsBetweenQuetes | word) <~ opt(propertySeparator)
  val propertyDefinitions = rep(propertyName ~! "=" ~! propertyValue)

  val columnDefinition = columnName ~! propertyDefinitions ^^ {
    case colName ~ properties =>
      val propMap = properties.map {
        case pName ~ _ ~ pValue => (pName, pValue)
      }.toMap

      new ColumnDefinition(colName, propMap)
  }

  val tableDef: Parser[List[ColumnDefinition]] = rep(columnDefinition)

  def parse(definition: String) : List[ColumnDefinition] = {
    parseAll(phrase(tableDef), definition) match {
      case Success(res, _) => res
      case failure => throw new RuntimeException(failure.toString)
    }
  }

  def parseJ(definition: String): java.util.List[ColumnDefinition] = JavaConversions.seqAsJavaList(parse(definition))
}

case class ColumnDefinition(name: String, properties: Map[String, String]) {
  //Scala to Java interop
  def getName = name
  def getProperties: java.util.Map[String, String] = JavaConversions.mapAsJavaMap(properties)
}
