package edu.bluejack22_2.nitip.ViewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import edu.bluejack22_2.nitip.Facade.Error;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.Model.GroupRow;
import edu.bluejack22_2.nitip.Model.Titip;
import edu.bluejack22_2.nitip.Model.TitipDetail;
import edu.bluejack22_2.nitip.Repository.TitipRepository;
import edu.bluejack22_2.nitip.Service.TimeService;

public class TitipViewModel {

    MutableLiveData<List<Titip>> titipLiveDatas;
    MutableLiveData<Titip> titipLiveData;
    private TitipRepository titipRepository;
    public TitipViewModel() {
        titipRepository = new TitipRepository();
        titipLiveDatas = new MutableLiveData<>();
        titipLiveData = new MutableLiveData<>();
    }

    public Response CreateTitip(Titip titip) {

        Response response = new Response(null);

        if (titip.getTitip_name().trim().isEmpty() || titip.getClose_time().trim().isEmpty()) {
            response.setError(new Error("All field must be filled"));
        }
        else if (!TimeService.isValidDate(titip.getClose_time())) {
            response.setError(new Error("Date must in the future"));
        }

        if (response.getError() != null) return response;

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String entrusterEmail = currentUser.getEmail();

        titip.setEntruster_email(entrusterEmail);

        titipRepository.CreateTitip(titip);

        return response;
    }

    public MutableLiveData<List<Titip>> getTitipLiveData() {
        getTitipData();
        return titipLiveDatas;
    }

    public void getTitipData() {
        titipRepository.getTitips(titipLiveDatas);
    }

    public MutableLiveData<Titip> getTitipById(String titipId) {
        titipLiveData = (MutableLiveData<Titip>) titipRepository.getTitipByID(titipId).getResponse();
        return titipLiveData;
    }

    public void addNewTitipDetail(String titipId, TitipDetail titipDetail) {
        titipRepository.addNewTitipDetail(titipId, titipDetail);
    }

    public CompletableFuture<Response> getLastTitip(String groupCode) {
        return titipRepository.getLastTitip(groupCode);
    }
}
