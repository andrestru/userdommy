package com.example.dummy.domain.model.repository;

import com.example.dummy.domain.model.SaveUser;

public interface LoginRepository {

    SaveUser save(SaveUser request);

}
