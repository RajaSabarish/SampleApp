package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Form._
import play.api.data.Forms._
import models._
import scala.concurrent.Future

object Tickets extends Controller {

  val ticket_form = Form(mapping(
    "id" -> optional(number),
    "CreatedBy" -> text,
    "Description" -> text,
    "Severity" -> number,
    "Status" -> text,
    "CancelledReason" -> text,
    "CancelledOtherDescription" -> text,
    "Comments" -> text)(Ticket.apply)(Ticket.unapply))

  def showTickets() = Action { implicit request =>
    val list = Ticket.select_all()
    Ok(views.html.showTickets(list))
  }

  def add() = Action { implicit request =>
    Ok(views.html.addTicket(ticket_form))
  }

  def addTicket() = Action { implicit request =>
    ticket_form.bindFromRequest.fold(
      form_with_errors => BadRequest(views.html.addTicket(form_with_errors)),
      form_value => {
          Ticket.insert(form_value)
        Redirect("/list")
      }
    )
  }

}

