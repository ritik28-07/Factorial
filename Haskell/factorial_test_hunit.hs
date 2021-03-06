module Main where

import Control.Exception
import Control.Monad
import Test.HUnit

import Factorial

positiveData = [
        (0, 1),
        (1, 1),
        (2, 2),
        (3, 6),
        (4, 24),
        (5, 120),
        (6, 720),
        (7, 5040),
        (8, 40320),
        (9, 362880),
        (10, 3628800),
        (11, 39916800),
        (12, 479001600),
        (13, 6227020800),
        (14, 87178291200),
        (20, 2432902008176640000),
        (30, 265252859812191058636308480000000),
        (40, 815915283247897734345611269596115894272000000000)
       ]

negativeData = [ -1, -2, -5, -10, -20, -100]

testPositive function comment = test [comment ++ " " ++ show i ~: "" ~: expected ~=? function i | (i, expected) <- positiveData]

-- assertException from: http://stackoverflow.com/questions/6147435/is-there-an-assertexception-in-any-of-the-haskell-test-frameworks/6147930#6147930
assertException :: (Exception e, Eq e) => e -> IO a -> IO ()
assertException ex action =
    handleJust isWanted (const $ return ()) $ do
        action
        assertFailure $ "Expected exception: " ++ show ex
  where isWanted = guard . (== ex)

testNegative function comment = test [comment ++ " " ++ show i ~: "" ~: assertException (ErrorCall "Factorial not defined for negative integers.") (evaluate $ function i) | i <- negativeData]

main = do
 runTestTT (testPositive Factorial.iterative "Iterative")
 runTestTT (testNegative Factorial.iterative "Iterative")
 runTestTT (testPositive Factorial.naïveRecursive "Naïve Recursive")
 runTestTT (testNegative Factorial.naïveRecursive "Naïve Recursive")
 runTestTT (testPositive Factorial.tailRecursive "Tail Recursive")
 runTestTT (testNegative Factorial.tailRecursive "Tail Recursive")
