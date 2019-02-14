package ftnjps.scientificcenter.journal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/journals")
public class JournalController {

	@Autowired
	private JournalService journalService;

	@GetMapping
	public ResponseEntity<List<Journal>> getAll() {
		List<Journal> journals = journalService.findAll();
		if(journals == null || journals.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(journals, HttpStatus.OK);
	}

}
