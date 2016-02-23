package models

import java.sql._
import play.api.db._
import anorm._
import anorm.SqlParser._
import play.api.mvc._
import play.api._
import play.api.Play.current

case class Ticket(
  id: Option[Int],
  CreatedBy: String,
  Description: String,
  Severity: Int,
  Status: String,
  CancelledReason: String,
  CancelledOtherDescription: String,
  Comments: String
  )

object Ticket{
  def select_all() = DB.withConnection { implicit connection =>
    val result = SQL("Select * from ticket")
    result().map( row => Ticket(
      row[Option[Int]]("ticket.id"),
      row[String]("ticket.createdBy"),
      row[String]("ticket.description"),
      row[Int]("ticket.severity"),
      row[String]("ticket.status"),
      row[String]("ticket.cancelledReason"),
      row[String]("ticket.cancelledOtherDescription"),
      row[String]("ticket.comments")
    )).toList
  }

  def insert(ticket: Ticket) = DB.withConnection { implicit connection =>
    SQL("insert into ticket(createdBy, description, severity, status, cancelledReason, cancelledOtherDescription, comments) values({createdBy}, {description}, {severity}, {status}, {cancelledReason}, {cancelledOtherDescription}, {comments})").on(
      'createdBy ->ticket.CreatedBy,
      'description ->ticket.Description,
      'severity ->ticket.Severity,
      'status ->ticket.Status,
      'cancelledReason ->ticket.CancelledReason,
      'cancelledOtherDescription ->ticket.CancelledOtherDescription,
      'comments ->ticket.Comments
    ).executeInsert(scalar[Long].single)
  }

}