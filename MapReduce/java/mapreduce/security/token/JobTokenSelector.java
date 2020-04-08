package org.apache.hadoop.mapreduce.security.token;

import java.util.Collection;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.security.token.Token;
import org.apache.hadoop.security.token.TokenIdentifier;
import org.apache.hadoop.security.token.TokenSelector;

/**
 * Look through tokens to find the first job token that matches the service
 * and return it.
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class JobTokenSelector implements TokenSelector<JobTokenIdentifier> {

  @SuppressWarnings("unchecked")
  @Override
  public Token<JobTokenIdentifier> selectToken(Text service,
      Collection<Token<? extends TokenIdentifier>> tokens) {
    if (service == null) {
      return null;
    }
    for (Token<? extends TokenIdentifier> token : tokens) {
      if (JobTokenIdentifier.KIND_NAME.equals(token.getKind())
          && service.equals(token.getService())) {
        return (Token<JobTokenIdentifier>) token;
      }
    }
    return null;
  }
}
