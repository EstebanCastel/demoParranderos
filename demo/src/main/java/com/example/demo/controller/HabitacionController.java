package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.modelo.Habitacion;
import com.example.demo.modelo.TipoHabitacion;
import com.example.demo.repositorio.HabitacionRepository;
import com.example.demo.repositorio.TipoHabitacionRepository;
import com.example.demo.service.SequenceGeneratorService;

@Controller
public class HabitacionController {

    @Autowired
    private HabitacionRepository habitacionRepository;
    @Autowired
    private TipoHabitacionRepository tipoHabitacionRepository;
    @Autowired
    private SequenceGeneratorService sequenceGenerator;

    @GetMapping("/habitaciones")
    public String obtenerTodasLasHabitaciones(Model model) {
        model.addAttribute("habitaciones", habitacionRepository.findAll());
        return "habitaciones";
    }

    @GetMapping({"/crearHabitacion", "/editarHabitacion"})
    public String mostrarFormularioHabitacion(@RequestParam(required = false) String id, Model model) {
        if (id != null) {
            Habitacion habitacion = habitacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de Habitación inválido: " + id));
            model.addAttribute("habitacion", habitacion);
        } else {
            model.addAttribute("habitacion", new Habitacion());
        }
        model.addAttribute("tiposHabitacion", tipoHabitacionRepository.findAll());
        return "habitacionForm";
    }

    @PostMapping("/guardarHabitacion")
    public String guardarHabitacion(@ModelAttribute("habitacion") Habitacion habitacion) {
        if (habitacion.getId() == null || habitacion.getId().isEmpty()) {
            // Genera un nuevo ID solo si es una nueva habitación
            habitacion.setId(String.valueOf(sequenceGenerator.generateSequence(Habitacion.SEQUENCE_NAME)));
        }
        // Busca y establece el tipo de habitación
        TipoHabitacion tipoHabitacion = tipoHabitacionRepository.findById(habitacion.getTipoHabitacion().getId())
                                      .orElseThrow(() -> new IllegalArgumentException("Tipo de Habitación inválido"));
        habitacion.setTipoHabitacion(tipoHabitacion);
        // Guarda la habitación
        habitacionRepository.save(habitacion);
        return "redirect:/habitaciones";
    }

    @PostMapping("/eliminarHabitacion")
    public String eliminarHabitacion(@RequestParam(name = "id") String id) {
        habitacionRepository.deleteById(id);
        return "redirect:/habitaciones";
    }
}
