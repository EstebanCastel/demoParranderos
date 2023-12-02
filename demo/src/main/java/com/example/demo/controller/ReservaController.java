package com.example.demo.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.modelo.Reserva;
import com.example.demo.modelo.TipoHabitacion;
import com.example.demo.repositorio.ReservaRepository;
import com.example.demo.repositorio.TipoHabitacionRepository;
import com.example.demo.service.SequenceGeneratorService;

@Controller
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private TipoHabitacionRepository tipoHabitacionRepository;
    @Autowired
    private SequenceGeneratorService sequenceGenerator;

    @GetMapping("/reservas")
    public String obtenerTodasLasReservas(Model model) {
        model.addAttribute("reservas", reservaRepository.findAll());
        return "reservas";
    }

    @GetMapping({"/crearReserva", "/editarReserva"})
    public String mostrarFormularioReserva(@RequestParam(required = false) String id, Model model) {
        Reserva reserva;
        if (id != null && !id.isEmpty()) {
            reserva = reservaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID de Reserva inválido: " + id));
        } else {
            reserva = new Reserva();
        }
        model.addAttribute("reserva", reserva);
        model.addAttribute("tiposHabitacion", tipoHabitacionRepository.findAll());
        return "reservaForm";
    }

    @PostMapping("/guardarReserva")
    public String guardarReserva(@ModelAttribute("reserva") Reserva reserva) {
        if (reserva.getId() == null || reserva.getId().isEmpty()) {
            reserva.setId(String.valueOf(sequenceGenerator.generateSequence(Reserva.SEQUENCE_NAME)));
        }
        // Asegúrate de que el tipoHabitacion de la reserva ya esté establecido o establecerlo aquí si es necesario
        reservaRepository.save(reserva);
        return "redirect:/reservas";
    }

    @PostMapping("/eliminarReserva")
    public String eliminarReserva(@RequestParam(name = "id") String id) {
        reservaRepository.deleteById(id);
        return "redirect:/reservas";
    }
}

