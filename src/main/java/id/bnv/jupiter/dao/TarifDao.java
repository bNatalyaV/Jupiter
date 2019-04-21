package id.bnv.jupiter.dao;

import id.bnv.jupiter.pojo.PhoneNumber;
import id.bnv.jupiter.pojo.Tarif;
import id.bnv.jupiter.pojo.TarifInfo;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@SuppressWarnings(value = {"unchecked"})
public class TarifDao extends Dao {

    @Autowired
    public TarifDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Tarif getTarifByNumber(PhoneNumber phoneNumber) {
        Query query = getSession().createQuery("from PhoneNumber u where u.tarifId=:tarifId");
        query.setParameter("tarifId", phoneNumber.tarifId);
        List<Tarif> listOfTarif = query.list();
        return listOfTarif.isEmpty() ? null : listOfTarif.get(0);
    }

    public Tarif addTarifForNumber(PhoneNumber number, int idTarif) {
        number.tarifId = idTarif;
//        getSession().saveOrUpdate();
        create(tarif);
        return tarif;
    }

    public void changeTariff(PhoneNumber number, Tarif tariff) {
        number.tarifId = tariff.tarifId;
        update(number);
    }

    public TarifInfo getInfo(Integer tarifId ) {
        Query query = getSession().createQuery("from Tarif u where u.tarifInfoId=:tarifInfoId");
        query.setParameter("tarifInfoId", tarifId);
        List<TarifInfo> list = query.list();
        return list.get(0);
    }
}
