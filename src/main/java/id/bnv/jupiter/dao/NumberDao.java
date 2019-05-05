package id.bnv.jupiter.dao;

import id.bnv.jupiter.pojo.*;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class NumberDao extends Dao {

    @Autowired
    public NumberDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<PhoneNumber> getAllNumbersOfUser(User user) {
        Query query = getSession().createQuery("from PhoneNumber u where u.userId=:userId");
        query.setParameter("userId", user.id);
        List<PhoneNumber> phoneNumbers = query.list();
        return phoneNumbers;
    }

    public PhoneNumber getNumberById(int id) {
        PhoneNumber phoneNumber = getSession().get(PhoneNumber.class, id);
        return phoneNumber;
    }

    public List<PhoneNumber> getNumberByUserId(int userId) {
        Query query = getSession().createQuery("from PhoneNumber u where u.userId=:userId");
        query.setParameter("userId", userId);
        List<PhoneNumber> list = query.list();
        return list;
    }

    public PhoneNumber addNumber(PhoneNumber number) {
        number.balance = 100;
        create(number);
        return number;
    }

    public PhoneNumber deleteNumber(User user, PhoneNumber number) {
        number.userId = user.id;
        delete(number);
        return number;
    }

    //7request
    public InfoAboutNumber getInfoAboutNumberByNumberId(int numberId) {
        Session session = getSession();
        PhoneNumber phoneNumber = session.get(PhoneNumber.class, numberId);
        String number = phoneNumber.phoneNumber;
        Tarif tarif = session.get(Tarif.class, phoneNumber.tarifId);
        TarifInfo tarifInfo = session.get(TarifInfo.class, tarif.tarifInfoId);
        String tarifName = tarifInfo.tarifName;
        String providerName = session.get(Provider.class, tarifInfo.providerId)
                .providerName;
        InfoAboutNumber infoAboutNumber = new InfoAboutNumber(number, providerName, tarifName);
        return infoAboutNumber;
    }

    // vk request
    public List<FullInfoAboutNumber> getFullInfoAboutNumber(int userId) {
        List<PhoneNumber> phoneNumbers = getNumberByUserId(userId);
        List<FullInfoAboutNumber> list = new ArrayList<>();
        Session session = getSession();
        for (PhoneNumber number : phoneNumbers) {
            Tarif tarif = session.get(Tarif.class, number.tarifId);
            Region region = session.get(Region.class, tarif.regionId);
            Country country = session.get(Country.class, region.countryId);
            TarifInfo tarifInfo = session.get(TarifInfo.class, tarif.tarifInfoId);
            Provider provider = session.get(Provider.class, tarifInfo.providerId);
            FullInfoAboutNumber info = new FullInfoAboutNumber(
                    number.phoneNumber,
                    country.countryName,
                    region.regionName,
                    provider.providerName,
                    tarifInfo.tarifName,
                    tarif.tarifPrice,
                    tarif.tarifId,
                    country.countryId
            );
            list.add(info);
        }
        return list;
    }
}