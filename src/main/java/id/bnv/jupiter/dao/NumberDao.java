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
import java.util.Random;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class NumberDao extends Dao {

    @Autowired
    public NumberDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<PhoneNumber> getAllNumbersOfUser(int userId) {
        Query query = getSession().createQuery("from PhoneNumber u where u.userId=:userId");
        query.setParameter("userId", userId);
        return (List<PhoneNumber>) query.list();
    }

    public PhoneNumber getNumberById(int id) {
        return getSession().get(PhoneNumber.class, id);
    }

    public List<PhoneNumber> getNumberByUserId(int userId) {
        Query query = getSession().createQuery("from PhoneNumber u where u.userId=:userId");
        query.setParameter("userId", userId);
        return (List<PhoneNumber>) query.list();
    }

    public PhoneNumber addNumber(PhoneNumber number) {
        Random random = new Random();
        number.balance = -100 + random.nextInt(200);
        create(number);
        return number;
    }

    public PhoneNumber deleteNumber(User user, PhoneNumber number) {
        number.userId = user.id;
        delete(number);
        return number;
    }

    public InfoAboutNumber getInfoAboutNumberByNumberId(int numberId) {
        Session session = getSession();
        PhoneNumber phoneNumber = session.get(PhoneNumber.class, numberId);
        String number = phoneNumber.phoneNumber;
        Tarif tarif = session.get(Tarif.class, phoneNumber.tarifId);
        TarifInfo tarifInfo = session.get(TarifInfo.class, tarif.tarifInfoId.tarifInfoId);
        String tarifName = tarifInfo.tarifName;
        String providerName = session.get(Provider.class, tarifInfo.providerId)
                .providerName;
        InfoAboutNumber infoAboutNumber = new InfoAboutNumber(number, providerName, tarifName);
        return infoAboutNumber;
    }

    public List<FullInfoAboutNumber> getFullInfoAboutNumber(int userId) {
        List<PhoneNumber> phoneNumbers = getNumberByUserId(userId);
        List<FullInfoAboutNumber> list = new ArrayList<>();
        Session session = getSession();
        for (PhoneNumber number: phoneNumbers) {
            Tarif tarif = session.get(Tarif.class, number.tarifId);
            Region region = session.get(Region.class, tarif.regionId);
            Country country = session.get(Country.class, region.countryId);
            TarifInfo tarifInfo = session.get(TarifInfo.class, tarif.tarifInfoId.tarifInfoId);
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

    public boolean isNumberAlreadyExist(PhoneNumber number) {
        return !getSession()
                .createQuery("from PhoneNumber p where p.phoneNumber = :number")
                .setParameter("number", number.phoneNumber)
                .list()
                .isEmpty();
    }
}