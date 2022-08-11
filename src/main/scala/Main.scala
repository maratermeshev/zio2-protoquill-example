import database.dao.{DataService, DataServiceLive, QuillContext}
import zio.ZIOAppDefault

/**
 * Demonstrates using Quill with a ZIO Module 2.0 pattern.
 *
 * Note, this has to be in a separate file otherwise this Dotty fails with:
 * "Cannot call macro trait DataService defined in the same source file"
 */
object Main extends ZIOAppDefault {
  def run =
    DataService.getPeople(List("name"), Map())
      .provide(QuillContext.dataSourceLayer, DataServiceLive.layer)
      .debug("Result")
      .exitCode
}
