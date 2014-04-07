function [ndw,ndb] = learnbm(p,d,lr,mc,dw,db)
%LEARNBM  Backpropagation Learning Rule with Momentum:
%           dW(i,j) = mc*dW(i,j) + (1-mc)*lr*D(i)*P(j)
%         (See LEARNBP, TRAINBPM, TRAINBPX)
%
%         [NdW,NdB] = LEARNBM(P,D,LR,MC,dW,dB)
%           P  - RxQ matrix of input vectors.
%           D  - SxQ matrix of error vectors.
%           lr - the learning rate.
%           mc - momentum constant.
%           dW - SxR weight change matrix.
%           dB - Sx1 bias change vector.
%         Returns:
%           NdW - a new weight change matrix.
%           NdM - a new bias change vector.
%
%         LEARNBM(P,D,LR,MC,dW)
%         Returns a new weight change matrix.

%         M.H. Beale & H.B. Demuth, 1-31-92
%         Copyright (c) 1992-93 by the MathWorks, Inc.

if (nargout==1 & nargin~=5) | (nargout==2 & nargin~=6)
  error('Wrong number of arguments.');
  end

x = (1-mc)*lr*d;
ndw = mc*dw + x*p';
if nargout == 2
  [pr,pc] = size(p);
  ndb = mc*db + x*ones(pc,1);
  end
