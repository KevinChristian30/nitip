package edu.bluejack22_2.nitip.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.bluejack22_2.nitip.Facade.Error;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.Model.Titip;
import edu.bluejack22_2.nitip.Repository.TitipRepository;
import edu.bluejack22_2.nitip.Service.TimeService;

public class TitipViewModel {

    private TitipRepository titipRepository;

    public TitipViewModel() {
        titipRepository = new TitipRepository();
    }

    public Response CreateTitip(Titip titip) {

        Response response = new Response(null);

        if (titip.getTitipName().trim().isEmpty() || titip.getCloseTime().trim().isEmpty()) {
            response.setError(new Error("All field must be filled"));
        }
        else if (!TimeService.isValidDate(titip.getCloseTime())) {
            response.setError(new Error("Date must in the future"));
        }

        if (response.getError() != null) return response;

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String entrusterEmail = currentUser.getEmail();

        titip.setEntrusterEmail(entrusterEmail);

        titipRepository.CreateTitip(titip);

        return response;
    }
}
