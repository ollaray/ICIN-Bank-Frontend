package com.userfront.service.serviceImpl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userfront.dao.AppointmentDao;
import com.userfront.domain.Appointment;
import com.userfront.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService{
	
	@Autowired
	private AppointmentDao appointmentDao;
	

	public void createAppointment(Appointment appointment) {
		appointmentDao.save(appointment);
	}
	
	public List<Appointment> findAll() {
		return appointmentDao.findAll();
	}
	
	public Appointment findAppointment(Long id) {
		Appointment appointment = appointmentDao.findById(id).orElseThrow(EntityNotFoundException::new);
		return appointment;
	}

	public void confirmAppointment(Long id) {
		Appointment appointment = findAppointment(id);
		appointment.setConfirmed(true);
		appointmentDao.save(appointment);
		
	}
	

}
