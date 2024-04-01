package proCollab.projectManagement.capstoneProject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proCollab.projectManagement.capstoneProject.model.Note;
import proCollab.projectManagement.capstoneProject.model.User;
import proCollab.projectManagement.capstoneProject.repository.NoteRepository;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> getAllNotesByUser(User user) {
        return noteRepository.findByNoteOwner(user);
    }

    @Override
    public Optional<Note> getNoteById(Long id) {
        return noteRepository.findById(id);
    }

    @Override
    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public Note updateNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }
}
