package com.example.emrsupportapp.interfaces;

import com.example.emrsupportapp.activities.TicketTodo;

public interface TicketOnClickListener {
    void onClickListenerTicket(int position, TicketTodo ticketTodo);

    void onLongClickListenerTicket(int position, TicketTodo ticketTodo);
}
