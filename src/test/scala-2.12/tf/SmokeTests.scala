package tf

import org.scalatest._

class SmokeTests extends FlatSpec with Matchers {

  "A call, monday, new user" should ("cost c1") in {
    val length = 60
    val age = 30
    val c1 = 10
    val c2 = 5
    val call = Call(Day(true, false), User(age), length, 0)
    val plan = new ScriptableCostPlan(
      Map("c1" -> NDouble(c1), "c2" -> NDouble(c2)),
      NBinop("*",
        NInspect("call_length"),
        NIf(
          NInspect("is_workday"),
          NAttr("c1"),
          NIf(
            NBinop("<", NInspect("user_age"), NDouble(365)),
            NBinop("*", NDouble(0.9), NAttr("c2")),
            NAttr("c2")
          )
        )
      )
    )
    val result = plan(call)
    result.cost should be (length * c1)
  }

  "A call, saturday, new user" should ("cost c2 * 0.9") in {
    val length = 60
    val age = 30
    val c1 = 10
    val c2 = 5
    val call = Call(Day(false, false), User(age), length, 0)
    val plan = new ScriptableCostPlan(
      Map("c1" -> NDouble(c1), "c2" -> NDouble(c2)),
      NBinop("*",
        NInspect("call_length"),
        NIf(
          NInspect("is_workday"),
          NAttr("c1"),
          NIf(
            NBinop("<", NInspect("user_age"), NDouble(365)),
            NBinop("*", NDouble(0.9), NAttr("c2")),
            NAttr("c2")
          )
        )
      )
    )
    val result = plan(call)
    result.cost should be (length * c2 * 0.9)
  }


  "A call, saturday, old user" should ("cost c2") in {
    val length = 60
    val age = 400
    val c1 = 10
    val c2 = 5
    val call = Call(Day(false, false), User(age), length, 0)
    val plan = new ScriptableCostPlan(
      Map("c1" -> NDouble(c1), "c2" -> NDouble(c2)),
      NBinop("*",
        NInspect("call_length"),
        NIf(
          NInspect("is_workday"),
          NAttr("c1"),
          NIf(
            NBinop("<", NInspect("user_age"), NDouble(365)),
            NBinop("*", NDouble(0.9), NAttr("c2")),
            NAttr("c2")
          )
        )
      )
    )
    val result = plan(call)
    result.cost should be (length * c2)
  }
}
