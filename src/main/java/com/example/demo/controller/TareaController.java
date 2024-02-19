package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Nota;
import com.example.demo.model.Publication;
import com.example.demo.model.Tarea;
import com.example.demo.model.WorkSpace;
import com.example.demo.repository.NotaRepository;
import com.example.demo.repository.PublicationRepository;
import com.example.demo.repository.TareaRepository;
import com.example.demo.repository.WorkSpaceRepository;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/publication/tarea")
public class TareaController {
	

    @Autowired
    private TareaRepository tRep;

    @Autowired
    private PublicationRepository pRep;
    
    @Autowired
    private WorkSpaceRepository wkRep;
    
    /**
     * Find all tareas by workspace id
     * @param wsid
     * @return
     */
	@GetMapping(path = "/findByWorkSpaceId/{wsid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<DTO> findAllTareasByWsid(@PathVariable  int wsid) {
    	WorkSpace wk = wkRep.findById(wsid);
        List<Publication> publications = pRep.findByWorkSpaceOrderByIdDesc(wk);
        List<Tarea> tareas = new ArrayList<>();
        List<DTO> obtainedTareas = new ArrayList<>();
        
        for (Publication publication : publications) {
            if(tRep.findByPid(publication.getId()) != null) {
                tareas.add(tRep.findByPid(publication.getId()));
            }
        }
        
        for (Tarea t : tareas) {
            DTO obtainedTarea = new DTO();

            obtainedTarea.put("pId", t.getPid());
            obtainedTarea.put("name", t.getName());
            obtainedTarea.put("state", t.getState());
            obtainedTarea.put("description", t.getDescription());

			obtainedTareas.add(obtainedTarea);
		}
        
        return obtainedTareas;
    }
	
	/**
	 * Creates a new tarea
	 * @param wkDTO
	 * @param request
	 * @return
	 */
	@PostMapping(path = "/insertTarea", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO insertTarea(@RequestBody DTO nDTO, HttpServletRequest request) {
		DTO result = new DTO();
		Publication p = new Publication();
		WorkSpace wk = wkRep.findById(Integer.parseInt(nDTO.get("wkid").toString()));
		Publication savedPublication = new Publication();

		try {
			
			if(wk != null) {
				p.setWorkSpace(wk);
				savedPublication = pRep.save(p);
				
				if(savedPublication.getId() > 0) {
					Tarea tarea = new Tarea();
					tarea.setPid(savedPublication.getId());
					tarea.setName(nDTO.get("name").toString());
					tarea.setState(nDTO.get("state").toString());
					tarea.setDescription(nDTO.get("description").toString());

                    
                    tRep.save(tarea);
                    result.put("result", "success");
				}
				else {
					result.put("result", "No se ha podido crear Nota");
				}
			}
			else {
				result.put("result", "No se ha encontrado espacio de trabajo");
			}

		} catch (Exception e) {
			result.put("result", "No ha sido posible crear Nota: " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
    
    
    /**
     * Updates an existing note
     * @param nDTO DTO con los datos de la nota a actualizar.
     * @param request HttpServletRequest para obtener información de la solicitud.
     * @return DTO con el resultado de la operación de actualización.
     */
    @PutMapping(path = "/updateTarea", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO updateTarea(@RequestBody DTO nDTO, HttpServletRequest request) {
        DTO result = new DTO();

        try {
            int tareaId = Integer.parseInt(nDTO.get("pId").toString());
            Tarea tareaToUpdate = tRep.findByPid(tareaId);
            
            if (tareaToUpdate != null) {
            	tareaToUpdate.setName(nDTO.get("name").toString());
            	tareaToUpdate.setState(nDTO.get("state").toString());
            	tareaToUpdate.setDescription(nDTO.get("description").toString());

                tRep.save(tareaToUpdate);

                result.put("result", "success");
            } else {
                result.put("result", "No se encontró la tarea con el ID proporcionado");
            }
        } catch (Exception e) {
            result.put("result", "No ha sido posible actualizar la tarea: " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
	
}
