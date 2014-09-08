import lt.home.{ColumnDefinition, GembaseTableHeaderParser}
import org.scalatest.{FunSuite, Matchers}

/**
 *
 * @author Antanas Bastys
 */
class GembaseTableHeaderParserSuite extends FunSuite with Matchers {

  test("Definition of single column parsing") {
    val definition: String = """DEFINE FIELD SUBR_ID &
                               |        /DESCRIPTION = "SUBR_ID" &
                               |        /PROMPT = "SUBR_ID" &
                               |        /HEADING = "SUBR_ID" &
                               |        /DATATYPE = TEXT &
                               |        /LENGTH = 10 &
                               |        /POSITION = 1""".stripMargin
    val parseResult = new GembaseTableHeaderParser().parse(definition)
    parseResult shouldEqual List(ColumnDefinition(
      "SUBR_ID",
      Map(
        ("DESCRIPTION", "SUBR_ID"),
        ("PROMPT", "SUBR_ID"),
        ("HEADING", "SUBR_ID"),
        ("DATATYPE", "TEXT"),
        ("LENGTH", "10"),
        ("POSITION", "1"))))
  }

  test("Definition of multiple column parsing") {
    val definition: String = """DEFINE FIELD SUBR_ID &
                               |        /POSITION = 1
                               |
                               |DEFINE FIELD PROMO_CODE &
                               |        /POSITION = 11""".stripMargin
    val parseResult = new GembaseTableHeaderParser().parse(definition)
    parseResult shouldEqual List(
      ColumnDefinition("SUBR_ID", Map(("POSITION", "1"))),
      ColumnDefinition("PROMO_CODE", Map(("POSITION", "11"))))
  }

  test("Definition in single line") {
    val definition: String = """DEFINE FIELD SUBR_ID & /DESCRIPTION = "SUBR_ID" & /PROMPT = "SUBR_ID" & /HEADING = "SUBR_ID" & /DATATYPE = TEXT & /LENGTH = 10 & /POSITION = 1
                               |DEFINE FIELD PROMO_CODE & /DESCRIPTION = "PROMO_CODE" & /PROMPT = "PROMO_CODE" & /HEADING = "PROMO_CODE" & /DATATYPE = TEXT & /LENGTH = 5 & /POSITION = 11""".stripMargin
    val parseResult = new GembaseTableHeaderParser().parse(definition)

    parseResult should have size 2
    val List(subrId, promoCode) = parseResult

    subrId.name shouldEqual "SUBR_ID"
    subrId.properties should contain key "PROMPT"
    subrId.properties should contain key "LENGTH"

    promoCode.name shouldEqual "PROMO_CODE"
    promoCode.properties should contain key "PROMPT"
    promoCode.properties should contain key "LENGTH"
  }

/*  test("Definition with incorrect syntax") {
    val definition: String = """DE2FINE FIELD SUBR_ID &
                               |        EESCRIPTION = "SUBR_ID" &
                               |        PROMPT = "SUBR_ID" &
                               |        /HEADING = "SUBR_ID" &
                               |        /DATATYPE = TEXT &
                               |        /LENGTH = 10 &
                               |        /POSITION = 1""".stripMargin
    val p: GembaseTableHeaderParser = new GembaseTableHeaderParser()
    val parseResult = p.parse(definition)
    //parseResult shouldEqual List(ColumnDefinition("SUBR_ID", 1, 10))
  }*/
}
