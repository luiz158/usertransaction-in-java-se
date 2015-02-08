package br.eti.augusto.transactional;

import javax.transaction.Transactional;

@Transactional(value = Transactional.TxType.REQUIRED)
public class BeanRequired extends BeanBase {
}
