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

import com.example.demo.model.Publication;
import com.example.demo.model.Recordatorio;
import com.example.demo.model.WorkSpace;
import com.example.demo.repository.PublicationRepository;
import com.example.demo.repository.RecordatorioRepository;
import com.example.demo.repository.WorkSpaceRepository;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/publication/recordatorio")
public class RecordatorioController {

    @Autowired
    private RecordatorioRepository rRep;

    @Autowired
    private PublicationRepository pRep;
    
    @Autowired
    private WorkSpaceRepository wkRep;
    
    /**
     * Find all recordatorios by workspace id
     * @param wsid
     * @return
     */
	@GetMapping(path = "/findByWorkSpaceId/{wsid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<DTO> findAllRecordatoriosByWsid(@PathVariable int wsid) {
    	WorkSpace wk = wkRep.findById(wsid);
        List<Publication> publications = pRep.findByWorkSpaceOrderByIdDesc(wk);
        List<Recordatorio> recordatorios = new ArrayList<>();
        List<DTO> obtainedRecordatorios = new ArrayList<>();
        
        for (Publication publication : publications) {
            if(rRep.findByPid(publication.getId()) != null) {
            	recordatorios.add(rRep.findByPid(publication.getId()));
            }
        }
        
        for (Recordatorio r : recordatorios) {
            DTO obtainedRecordatorio = new DTO();

            obtainedRecordatorio.put("pId", r.getPid());
            obtainedRecordatorio.put("title", r.getTitle());
            obtainedRecordatorio.put("hour", r.getHour());
            obtainedRecordatorio.put("date", r.getDate());
            obtainedRecordatorio.put("completed", r.getCompleted());

            obtainedRecordatorios.add(obtainedRecordatorio);
		}
        
        return obtainedRecordatorios;
    }
	
	/**
	 * Creates a new recordario
	 * @param wkDTO
	 * @param request
	 * @return
	 */
	@PostMapping(path = "/insertRecordatorio", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO insertRecordatorio(@RequestBody DTO nDTO, HttpServletRequest request) {
		DTO result = new DTO();
		Publication p = new Publication();
		WorkSpace wk = wkRep.findById(Integer.parseInt(nDTO.get("wkid").toString()));
		Publication savedPublication = new Publication();

		try {
			
			if(wk != null) {
				p.setWorkSpace(wk);
				savedPublication = pRep.save(p);
				
				if(savedPublication.getId() > 0) {
					Recordatorio recordatorio = new Recordatorio();
					recordatorio.setPid(savedPublication.getId());
					recordatorio.setTitle(nDTO.get("title").toString());
					recordatorio.setCompleted(Byte.parseByte(nDTO.get("completed").toString()));
					recordatorio.setDate(nDTO.get("date").toString());
					recordatorio.setHour(nDTO.get("hour").toString());


                    
                    rRep.save(recordatorio);
                    result.put("result", "success");
				}
				else {
					result.put("result", "No se ha podido crear Recordatorio");
				}
			}
			else {
				result.put("result", "No se ha encontrado espacio de trabajo");
			}

		} catch (Exception e) {
			result.put("result", "No ha sido posible crear Recordatorio: " + e.getMessage());
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
    @PutMapping(path = "/updateRecordatorio", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO updateRecordatorio(@RequestBody DTO nDTO, HttpServletRequest request) {
        DTO result = new DTO();

        try {
            int tareaId = Integer.parseInt(nDTO.get("pId").toString());
            Recordatorio recordatorioToUpdate = rRep.findByPid(tareaId);
            
            if (recordatorioToUpdate != null) {
            	recordatorioToUpdate.setPid(Integer.parseInt(nDTO.get("pId").toString()));
            	recordatorioToUpdate.setTitle(nDTO.get("title").toString());
            	recordatorioToUpdate.setCompleted(Byte.parseByte(nDTO.get("completed").toString()));
            	recordatorioToUpdate.setDate(nDTO.get("date").toString());
            	recordatorioToUpdate.setHour(nDTO.get("hour").toString());

                rRep.save(recordatorioToUpdate);

                result.put("result", "success");
            } else {
                result.put("result", "No se encontró el recordatorio con el ID proporcionado");
            }
        } catch (Exception e) {
            result.put("result", "No ha sido posible actualizar el recordatorio: " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
	
	
}
