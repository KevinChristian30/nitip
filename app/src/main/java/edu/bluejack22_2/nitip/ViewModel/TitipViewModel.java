package edu.bluejack22_2.nitip.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import edu.bluejack22_2.nitip.Facade.Error;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.Model.GroupRow;
import edu.bluejack22_2.nitip.Model.Titip;
import edu.bluejack22_2.nitip.Repository.TitipRepository;
import edu.bluejack22_2.nitip.Service.TimeService;

public class TitipViewModel {

    private TitipRepository titipRepository;
    private MutableLiveData<List<Titip>> titipLiveData;
    public TitipViewModel() {
        titipRepository = new TitipRepository();
        titipLiveData = new MutableLiveData<>();
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

        titipRepository.CreateTitip(titip);

        return response;
    }

    public MutableLiveData<List<Titip>> getTitipLiveData() {
        getTitipData();
        return titipLiveData;
    }

    public void getTitipData() {
        titipRepository.GetTitip(titipLiveData);
    }
}
