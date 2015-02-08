package br.eti.augusto.transactional;

import javax.transaction.Transactional;

@Transactional(value = Transactional.TxType.NEVER)
public class BeanNever extends BeanBase {
}
