package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.modelo.Estadia;
import com.example.demo.repositorio.EstadiaRepository;
import com.example.demo.repositorio.ReservaRepository;
import com.example.demo.service.SequenceGeneratorService;


@Controller
public class EstadiaController {

    @Autowired
    private EstadiaRepository estadiaRepository;
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private SequenceGeneratorService sequenceGenerator;

    @GetMapping("/estadias")
    public String mostrarEstadias(Model model) {
        List<Estadia> listaEstadias = estadiaRepository.findAll();
        model.addAttribute("listaEstadias", listaEstadias);
        return "estadias";
    }


    @GetMapping({"/crearEstadia", "/editarEstadia"})
    public String mostrarFormularioEstadia(@RequestParam(required = false) String id, Model model) {
        Estadia estadia;
        if (id != null && !id.isEmpty()) {
            estadia = estadiaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID de Estadía inválido: " + id));
        } else {
            estadia = new Estadia();
        }
        model.addAttribute("estadia", estadia);
        model.addAttribute("reservas", reservaRepository.findAll());
        return "estadiaForm";
    }

    @PostMapping("/guardarEstadia")
    public String guardarEstadia(@ModelAttribute("estadia") Estadia estadia) {
        if (estadia.getId() == null || estadia.getId().isEmpty()) {
            estadia.setId(String.valueOf(sequenceGenerator.generateSequence(Estadia.SEQUENCE_NAME)));
        }
        // Otras validaciones y lógica de negocio pueden ser agregadas aquí
        estadiaRepository.save(estadia);
        return "redirect:/estadias";
    }

    @PostMapping("/eliminarEstadia")
    public String eliminarEstadia(@RequestParam(name = "id") String id) {
        estadiaRepository.deleteById(id);
        return "redirect:/estadias";
    }
}
