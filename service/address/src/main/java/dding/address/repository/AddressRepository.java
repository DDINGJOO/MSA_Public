package dding.address.repository;


import dding.address.config.AddressType;
import dding.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByReferenceIdAndAddressType(String referenceId, AddressType addressType);
    List<Address> findAllByAddressType(AddressType addressType);

    @Query("SELECT DISTINCT a.city FROM address a")
    List<String> findDistinctCities();

    @Query("SELECT DISTINCT a.district FROM address a WHERE a.city = :city")
    List<String> findDistrictsByCity(String city);

    @Query("SELECT DISTINCT a.neighborhood FROM address a WHERE a.city = :city AND a.district = :district")
    List<String> findNeighborhoodsByCityAndDistrict(String city, String district);



    // 특정 시의 구 목록 조회
    @Query("SELECT DISTINCT a.district FROM address a WHERE a.city = :city")
    List<String> findDistinctDistrictsByCity(String city);

    // 특정 시/구의 동 목록 조회
    @Query("SELECT DISTINCT a.neighborhood FROM address a WHERE a.city = :city AND a.district = :district")
    List<String> findDistinctNeighborhoodsByCityAndDistrict(String city, String district);
}
