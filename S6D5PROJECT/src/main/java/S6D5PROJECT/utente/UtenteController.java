package S6D5PROJECT.utente;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import S6D5PROJECT.payloads.UtenteInfoPayload;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

	@Autowired
	private UtenteService utenteService;

	@GetMapping
//	@PreAuthorize
	public ResponseEntity<List<Utente>> getUtenti() {
		List<Utente> listaUtenti = utenteService.find();
		if (!listaUtenti.isEmpty()) {
			return new ResponseEntity<>(listaUtenti, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/{utenteId}")
	ResponseEntity<UtenteInfoPayload> getUser(@PathVariable UUID utenteId) throws Exception {
		Utente found = utenteService.findById(utenteId);
		return new ResponseEntity<>(new UtenteInfoPayload(found.getUsername(), found.getNome(), found.getCognome(),
				found.getEmail(), found.getDipositiviAssegnati()), HttpStatus.OK);
	}

	@PutMapping("/{utenteId}")
	public Utente updateUser(@PathVariable UUID utenteId, @RequestBody UtenteInfoPayload body) throws Exception {
		return utenteService.findByIdAndUpdate(utenteId, body);
	}

	@DeleteMapping("/{utenteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable UUID utenteId) throws Exception {
		utenteService.findByIdAndDelete(utenteId);

	}

}