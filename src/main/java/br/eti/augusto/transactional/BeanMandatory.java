package br.eti.augusto.transactional;

import javax.transaction.Transactional;

@Transactional(value = Transactional.TxType.MANDATORY)
public class BeanMandatory extends BeanBase {
}
