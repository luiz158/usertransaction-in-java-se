package br.eti.augusto.transactional;

import javax.transaction.Transactional;

@Transactional(value = Transactional.TxType.SUPPORTS)
public class BeanSupports extends BeanBase {
}
