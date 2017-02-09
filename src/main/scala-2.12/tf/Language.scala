package tf

case class Day(isWorkday: Boolean, isHolidays: Boolean)
case class User(age: Int)
case class Call(date: Day, user: User, seconds: Int, cost: Double) {
  def withCost(c: Double) = Call(date, user, seconds, c)
}

abstract class Plan {
  def apply(call: Call) : Call
}

class Stack {
  var s = List[ASTNode]()
  def push(x: ASTNode): Unit = {
    s = x :: s
  }
  def pop() = s match {
    case List() => ???
    case x :: xs =>
      s = xs
      x
  }
  override def toString: String = s.toString()
}

abstract class ASTNode
case class NAttr(name: String) extends ASTNode
case class NInspect(param: String) extends ASTNode
case class NIf(cond: ASTNode, yes: ASTNode, no: ASTNode) extends ASTNode
case class NBinop(op: String, a: ASTNode, b: ASTNode) extends ASTNode
abstract class NValue extends ASTNode
case class NDouble(d: Double) extends NValue
case class NBoolean(b: Boolean) extends NValue

object AST {

  def inspect(call: Call, prop: String) = prop match {
    case "call_length" => NDouble(call.seconds)
    case "cost" => NDouble(call.cost)
    case "is_workday" => NBoolean(call.date.isWorkday)
    case "is_weekend" => NBoolean(!call.date.isWorkday)
    case "is_holidays" => NBoolean(call.date.isHolidays)
    case "user_age" => NDouble(call.user.age)
    case _ => ???
  }

  def traverse(stack: Stack, params: Map[String, ASTNode], call: Call, n: ASTNode, depth: Int): Unit = {
    val margin = "  " * depth
    println(s"${margin}Traversing ${n}")
    println(s"${margin}Stack before: ${stack}")
    n match {
      case v : NValue =>
        stack.push(v)
      case NBinop(op, left, right) =>
        traverse(stack, params, call, left, depth + 1)
        val a = stack.pop()
        traverse(stack, params, call, right, depth + 1)
        val b = stack.pop()
        val res = (op, a, b) match {
          case ("*", NDouble(a), NDouble(b)) => NDouble(a * b)
          case ("<", NDouble(a), NDouble(b)) => NBoolean(a < b)
          case (">", NDouble(a), NDouble(b)) => NBoolean(a > b)
          case _ => ???
        }
        stack.push(res)
      case NInspect(param) =>
        stack.push(inspect(call, param))
      case NIf(cond, yes, no) =>
        traverse(stack, params, call, cond, depth + 1)
        stack.pop() match {
          case NBoolean(false) => traverse(stack, params, call, no, depth + 1)
          case _ => traverse(stack, params, call, yes, depth + 1)
        }
      case NAttr(name) =>
        params.get(name) match {
          case Some(value) => stack.push(value)
          case _ => ???
        }
      case _ => ???
    }
    println(s"${margin}Stack after:  ${stack}")
  }
}

class ScriptableCostPlan(params: Map[String, ASTNode], cost: ASTNode) extends Plan {
  def apply(call: Call) = {
    val stack = new Stack()
    AST.traverse(stack, params, call, cost, 0)
    stack.pop() match {
      case NDouble(d) => call.withCost(call.cost + d)
      case _ => ???
    }
  }
}
