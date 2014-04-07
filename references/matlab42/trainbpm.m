function [a,b,c,d,e,f,g,h] = trainbpm(i,j,k,l,m,n,o,p,q,r,s,t)
%TRAINBPM Trains a network with 1-3 layers with backpropagation
%         with momentum until an error goal is reached or a
%         maximum training epoch has occured.
%         (See TRAINP, TRAINWH)
%         
%         [NW1,NB1,TE,TR] = TRAINBPM(W1,B1,F1,P,T,TP)
%           W1 - S1xR weight matrix.
%           B1 - S1x1 bias vector.
%           F1 - the layer's transfer function.
%           P  - RxQ matrix of input vectors.
%           T  - S1xQ matrix of target vectors.
%           TP - row vector of 6 training parameters:
%                [disp_freq max_epoch err_goal lr mom_const err_goal]
%         Returns:
%           NW1 - a new weight matrix.
%           NB1 - a new bias vector.
%           TE  - the actual number of epochs trained.
%           TR - training record: [row of errors]
%
%         [NW1,NB1,NW2,NB2,TE,TR] = TRAINBPM(W1,B1,F1,W2,B2,F2,P,T,TP)
%           ...trains a 2-layer network.
%
%         [NW1,NB1,NW2,NB2,NW3,NB3,TE,TR] =
%           TRAINBPM(W1,B1,F1,W2,B2,F2,W3,B3,F3,P,T,TP)
%           ...trains a 3-layer network.

%         M.H. Beale & H.B. Demuth, 1-31-92
%         Copyright (c) 1992-93 by the MathWorks, Inc.

if nargin == 6
  if length(n) ~= 6
    error('Wrong number of training parameters.')
    end
  if nargout == 3
    [a,b,c] = tbpm1(i,j,k,l,m,n);
  else
    [a,b,c,d] = tbpm1(i,j,k,l,m,n);
    end

elseif nargin == 9
  if length(q) ~= 6
    error('Wrong number of training parameters.')
    end
  if nargout == 5
    [a,b,c,d,e] = tbpm2(i,j,k,l,m,n,o,p,q);
  else
    [a,b,c,d,e,f] = tbpm2(i,j,k,l,m,n,o,p,q);
    end

elseif nargin == 12
  if length(t) ~= 6
    error('Wrong number of training parameters.')
    end
  if nargout == 7
    [a,b,c,d,e,f,g] = tbpm3(i,j,k,l,m,n,o,p,q,r,s,t);
  else
    [a,b,c,d,e,f,g,h] = tbpm3(i,j,k,l,m,n,o,p,q,r,s,t);
    end

else
  error('Wrong number of arguments.');
  end
