function [nw,nb,te,tr] = tbpm1(w,b,f,p,t,tp)
%TBPM1    Trains a 1-layer network with backpropagation
%         using momentum.
%         (Called by TRAINBPM)
%         
%         [NW,NB,TE,TR] = TBPM1(W,B,F,P,T,TP,ER)
%           W  - SxR weight matrix.
%           B  - Sx1 bias vector.
%           F  - name of the transfer function (string).
%           P  - RxQ matrix of input vectors.
%           T  - SxQ matrix of target vectors.
%           TP - row vector of 6 training parameters:
%                [disp_freq max_epoch err_goal lr mom_const err_ratio]
%         Returns:
%           NW,NB - new weights & biases.
%           TE - the actual number of epochs trained.
%           TR - training record: [row of errors]

%         M.H. Beale & H.B. Demuth, 1-31-92
%         Copyright (c) 1992-93 by the MathWorks, Inc.

if nargin ~= 6
  error('Wrong number of arguments.');
  end

% TRAINING PARAMETERS
df = tp(1);
me = tp(2);
eg = tp(3);
lr = tp(4);
mc = tp(5);
er = tp(6);

% NETWORK PARAMETERS
W = w;
B = b;
FP = getdelta(f);

dW = W*0;
dB = B*0;
MC = 0;

% PRESENTATION PHASE
A = feval(f,W*p,B);
E = t-A;
SSE = sumsqr(E);

% TRAINING RECORD
TR = zeros(1,me);
SSE0 = SSE;

% BACKPROPAGATION PHASE
D = feval(FP,A,E);

for epoch=1:me

  % CHECK PHASE
  if SSE < eg, epoch=epoch-1; break, end

  % LEARNING PHASE
  [dW,dB] = learnbpm(p,D,lr,MC,dW,dB);
  W = W + dW; B = B + dB;

  % PRESENTATION PHASE
  A = feval(f,W*p,B);
  E = t-A;
  NSSE = sumsqr(E);
  if (NSSE > er * SSE)
    MC = 0;
  else
    MC = mc;
    end
  SSE = NSSE;

  % BACKPROPAGATION PHASE
  D = feval(FP,A,E);

  % TRAINING RECORD
  TR(epoch) = SSE;

  % DISPLAY RESULTS
  if rem(epoch,df) == 0
    plottr([SSE0 TR(1,1:epoch)], ...
     '1-Layer Backpropagation with Momentum')
    end
  end

if rem(epoch,df)
  plottr([SSE0 TR(1,1:epoch)], ...
    '1-Layer Backpropagation with Momentum')
  end

% RETURN RESULTS
nw = W;
nb = B;
te = epoch;
tr = [SSE0 TR(1:epoch)];
