package com.userfront.service;

import java.util.List;

import com.userfront.domain.Appointment;

public interface AppointmentService {

	void createAppointment(Appointment appointment);
	List<Appointment> findAll();
	void confirmAppointment(Long id);
	Appointment findAppointment(Long id);

}
