package com.diplom.bookingsystem.repository;

import com.diplom.bookingsystem.model.Place.Place;
import com.diplom.bookingsystem.model.User.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

@RepositoryRestResource(exported = false)
@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    Boolean existsByName(String name);

    Optional<Place> findByName(String name);

    @Query("SELECT p from Place p where (:name is null or p.name = :name) and (:country is null or p.address.country = :country) and (:city is null or p.address.city = :city) and (:street is null or p.address.street = :street) and (:creatorName is null or p.user.name = :creatorName) and (:creatorSurname is null or p.user.surname = :creatorSurname)")
    //@Query("select place0_.place_id as place_id1_5_, place0_.address_id as address_4_5_, place0_.description as descript2_5_, place0_.name as name3_5_, place0_.user_id as user_id5_5_ from places place0_ left outer join addresses address1_ on place0_.address_id=address1_.address_id left outer join users user2_ on place0_.user_id=user2_.user_id where (place0_.name is null) and (address1_.country is null) and (address1_.city is null) and (address1_.street is null) and (user2_.name is null) and (user2_.surname is null)")
    Page<Place> findAllByNameAndAddress_CountryAndAddress_CityAndAddress_StreetAndUser_NameAndUser_Surname (
            @Param("name") String name,
            @Param("country") String country,
            @Param("city") String city,
            @Param("street") String street,
            @Param("creatorName") String creatorName,
            @Param("creatorSurname") String creatorSurname,
            Pageable pageable
    );
}
